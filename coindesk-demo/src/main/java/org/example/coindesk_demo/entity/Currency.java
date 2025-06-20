package org.example.coindesk_demo.entity;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "currency")
public class Currency {
    @Id
    private String code;
    private String chineseName;

    public Currency() { }

    public Currency(String code, String chineseName) {
        this.code = code;
        this.chineseName = chineseName;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getChineseName() {
        return chineseName;
    }
    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }
}
