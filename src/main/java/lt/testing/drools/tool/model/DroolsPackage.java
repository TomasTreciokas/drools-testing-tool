package lt.testing.drools.tool.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Objects;

// TODO: make it entity, dedicated to store and retrieve rules
@Entity
@Table(name = "DROOLS_PACKAGE")
public class DroolsPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sab_kopija")
    private byte[] sabKopija;

    public DroolsPackage() {
    }

    public DroolsPackage(String name, byte[] sabKopija) {
        this.name = name;
        this.sabKopija = sabKopija;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getSabKopija() {
        return sabKopija;
    }

    public void setSabKopija(byte[] content) {
        this.sabKopija = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DroolsPackage that = (DroolsPackage) o;
        return Objects.equals(name, that.name) && Arrays.equals(sabKopija, that.sabKopija);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(sabKopija);
        return result;
    }

    @Override
    public String toString() {
        return "DroolPackage{" +
                "name='" + name + '\'' +
                ", content=" + Arrays.toString(sabKopija) +
                '}';
    }
}
