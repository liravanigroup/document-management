package pl.com.bottega.documentmanagement.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by maciuch on 12.06.16.
 */
@Entity
public class Document {

    @Id
    @GeneratedValue
    private Long id;

    private DocumentNumber documentNumber;

    private String content;

    private String title;

    private boolean deleted;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt, verifiedAt, updatedAt;

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee creator;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee verificator;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee deletor;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Tag> tags;

    private Document() {
    }

    public Document(DocumentNumber documentNumber, String content, String title, Employee creator) {
        this.documentNumber = documentNumber;
        this.content = content;
        this.title = title;
        this.creator = creator;
        this.status = DocumentStatus.DRAFT;
        this.createdAt = new Date();
        this.deleted = false;
    }

    public void change(String title, String content) {
        this.title = title;
        this.content = content;
        this.status = DocumentStatus.DRAFT;
        this.updatedAt = new Date();
    }

    public void verify(Employee employee) {
        checkArgument(employee != null);
        this.verificator = employee;
        this.status = DocumentStatus.VERIFIED;
        this.verifiedAt = new Date();
    }

    public void confirm(Employee conirmator) {

    }

    public void confirm(Employee confirmator, Employee forEmployee) {

    }

    public void delete(Employee deletor) {
        this.deletor = deletor;
        this.deleted = true;
    }

    public void tag(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Tag> tags() {
        return tags;
    }

    public Employee verificator() {
        return verificator;
    }

    public String content() {
        return content;
    }

    public Employee creator() {
        return creator;
    }

    public String title() {
        return title;
    }

    public boolean deleted() {
        return deleted;
    }

    public DocumentNumber number() {
        return documentNumber;
    }

    public DocumentStatus status() {
        return status;
    }

    public Date verifiedAt() {
        return verifiedAt;
    }
}
