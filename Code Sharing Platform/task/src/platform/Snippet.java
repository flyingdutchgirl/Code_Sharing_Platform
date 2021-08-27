package platform;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
public class Snippet {

    @Id
    @Column(name = "snippetId")
    @GeneratedValue
    private long snippetId;

    @Column(name = "code")
    private String code;

    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    @Column(name = "numberOfViews")
    private long numberOfViews;

    @Column(name = "timeLimit")
    private long timeLimit;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "restricted")
    private boolean restricted;

    @Column(name = "timeRestriction")
    private boolean timeRestriction;

    @Column(name = "viewRestriction")
    private boolean viewRestriction;


    public Snippet(String code, long timeLimit, long numberOfViews) {
        this.code = code;
        this.numberOfViews = numberOfViews > 0 ? numberOfViews : 0;
        this.timeLimit = timeLimit > 0 ? timeLimit : 0;

        this.creationDate = LocalDateTime.now();
        this.uuid = UUID.randomUUID().toString();

        this.timeRestriction = timeLimit > 0;
        this.viewRestriction = numberOfViews > 0;

        this.restricted = timeRestriction || viewRestriction;
    }


    protected Snippet() {

    }

    public long timeLeft() {
        return timeRestriction
                ? timeLimit - ChronoUnit.SECONDS.between(this.creationDate, LocalDateTime.now())
                : 0;
    }

    public boolean isVisible() {
        boolean time = !timeRestriction || timeLeft() >= 0;
        boolean views = !viewRestriction || this.numberOfViews > 0;

        return time && views;
    }

    public void decreaseViewCounter() {
        this.numberOfViews--;
    }

    public long getTimeLimit() {
        return timeRestriction ? timeLimit : 0;
    }

    public void setTimeLimit(long timeLimit) {
        this.timeLimit = timeLimit;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }

    public String getCode() {
        return code;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getStringDate() {
        return removeTrailingZeros(this.creationDate.toString());
    }

    public long getSnippetId() {
        return snippetId;
    }

    public void setSnippetId(int snippetId) {
        this.snippetId = snippetId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setSnippetId(long snippetId) {
        this.snippetId = snippetId;
    }

    public long getNumberOfViews() {
        return viewRestriction ? numberOfViews : 0;
    }

    public void setNumberOfViews(long numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isTimeRestriction() {
        return timeRestriction;
    }

    public void setTimeRestriction(boolean timeRestriction) {
        this.timeRestriction = timeRestriction;
    }

    public boolean isViewRestriction() {
        return viewRestriction;
    }

    public void setViewRestriction(boolean viewRestriction) {
        this.viewRestriction = viewRestriction;
    }

    public static String removeTrailingZeros(String s) {
        return s.replaceFirst("\\.0*$|(\\.\\d*?)0+$", "$1");
    }

    @Override
    public String toString() {
        return "Snippet{" +
                "snippetId=" + snippetId +
                ", code='" + code + '\'' +
                ", creationDate=" + creationDate +
                ", numberOfViews=" + numberOfViews +
                ", timeLimit=" + timeLimit +
                ", uuid='" + uuid + '\'' +
                ", restricted=" + restricted +
                '}';
    }

}
