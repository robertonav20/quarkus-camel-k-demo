package demo.entities;



import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;

public class Telepass extends ParentClass{

    private String userId;
    private String licencePlate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss:SSS")
    private Date timestamp;


    public Telepass(){}

    public Telepass(String userId, String deviceId, Date timestamp, String licencePlate) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.licencePlate = licencePlate;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId,timestamp);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
        {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()){
            return false;
        }
        final Telepass that = (Telepass) obj;
        return Objects.equals(userId,that.userId) &&
                Objects.equals(licencePlate, that.licencePlate) &&
                Objects.equals(timestamp, that.timestamp) ;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Telepass{");
        sb.append("userId=").append(userId);
        sb.append(", licencePlate=").append(licencePlate);
        sb.append(", timestamp=").append(timestamp);
        sb.append("}");
        return sb.toString();
    }
}
