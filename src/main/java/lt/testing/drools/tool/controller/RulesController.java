package lt.testing.drools.tool.controller;

import lt.testing.drools.tool.model.DroolPackageRequest;
import lt.testing.drools.tool.model.DroolsData;
import lt.testing.drools.tool.model.DroolsPackage;
import lt.testing.drools.tool.model.DroolsPackageResponse;
import lt.testing.drools.tool.model.OrderDiscount;
import lt.testing.drools.tool.model.OrderRequest;
import lt.testing.drools.tool.model.PackageInfoResponse;
import lt.testing.drools.tool.model.RuleExecutionRequest;
import lt.testing.drools.tool.model.TaxiRide;
import lt.testing.drools.tool.service.DroolsPackageRepository;
import lt.testing.drools.tool.service.OrderDiscountService;
import lt.testing.drools.tool.service.TaxiFareCalculatorService;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class RulesController {

    private final OrderDiscountService orderDiscountService;
    private final TaxiFareCalculatorService taxiFareCalculatorService;
    private final DroolsPackageRepository droolsPackageRepository;
    private static final KieServices kieServices = KieServices.Factory.get();

    @Autowired
    public RulesController(
            OrderDiscountService orderDiscountService,
            TaxiFareCalculatorService taxiFareCalculatorService,
            DroolsPackageRepository droolsPackageRepository
    ) {
        this.orderDiscountService = orderDiscountService;
        this.taxiFareCalculatorService = taxiFareCalculatorService;
        this.droolsPackageRepository = droolsPackageRepository;
    }

    @PostMapping("/get-discount")
    public ResponseEntity<OrderDiscount> getDiscount(@RequestBody OrderRequest orderRequest) {
        OrderDiscount orderDiscount = orderDiscountService.getOrderDiscount(orderRequest);
        return new ResponseEntity<>(orderDiscount, HttpStatus.OK);
    }

    @PostMapping("/get-taxi-fare")
    public ResponseEntity<Long> getTaxiFare(@RequestBody TaxiRide taxiRide) {
        Long calculatedFare = taxiFareCalculatorService.calculateFare(taxiRide);
        return new ResponseEntity<>(calculatedFare, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/drools-packages")
    public ResponseEntity<PackageInfoResponse> getRulePackages() {
        List<DroolPackageRequest> packagesResponse = droolsPackageRepository.findAll()
                .stream()
                .map(pr -> new DroolPackageRequest(
                                pr.getId(),
                                pr.getName(),
                                pr.getSabKopija().toString(),
                                "ready"))
                .collect(Collectors.toList());

        return new ResponseEntity<>(new PackageInfoResponse(packagesResponse), HttpStatus.OK);
    }

    @PostMapping(value = "/drools/new-rule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DroolsPackageResponse> addRulePackage(@RequestBody DroolPackageRequest request) {
        String name = request.getName();
        byte[] content = Base64.getDecoder().decode(request.getTaskDescription());

        DroolsPackage model = droolsPackageRepository.save(new DroolsPackage(name, content));

        return new ResponseEntity<>(new DroolsPackageResponse(model.getName(), model.getSabKopija()), HttpStatus.OK);
    }

    @PostMapping(value = "/drools/execute-rule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> executeRulePackage(@RequestBody RuleExecutionRequest request) {
        Optional<DroolsPackage> droolsPackage = droolsPackageRepository.getByName(request.getPackageName());

        List<String> firedRules = droolsPackage.map(dp -> {
                    Long response = calculateFareMap(dp, request.getDroolsData());
                    return List.of(response.toString());
                }
        ).orElse(new ArrayList<>());

        return new ResponseEntity<>(firedRules.toString(), HttpStatus.OK);
    }

    public Long calculateFareMap(DroolsPackage packageDrools, DroolsData droolsData) {
        ArrayList<String> firedRules = new ArrayList<>();
        KieSession kieSession = kieContainerByPackage(packageDrools.getSabKopija()).newKieSession();
        kieSession.setGlobal("firedRules", firedRules);
        kieSession.insert(droolsData);
        kieSession.fireAllRules();
        kieSession.dispose();

        return rideFare.getTotalFare();
    }

    private KieContainer kieContainerByPackage(byte[] droolsPackageContent) {
        File outputFile = new File("rule.drl");

        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            outputStream.write(droolsPackageContent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newFileResource(outputFile));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();

        return kieServices.newKieContainer(kieModule.getReleaseId());
    }
}
