package demo.entities;







import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;

public class Totem extends ParentClass{

    private Telepass telepass;
    private String totemId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss:SSS")
    private Date accessTime;

    public Totem(){
    }
    public Totem(final Telepass telepass, final String totemId, final Date accessTime) {
        this.telepass = telepass;
        this.totemId = totemId;
        this.accessTime = accessTime;

    }

    public Telepass getTelepass() {
        return telepass;
    }

    public void setTelepass(Telepass telepass) {
        this.telepass = telepass;
    }

    public String getTotemId() {
        return totemId;
    }

    public void setTotemId(String totemId) {
        this.totemId = totemId;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }


    @Override
    public int hashCode() {
        return Objects.hash(telepass,totemId,accessTime);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        final Totem that = (Totem) o;
        return Objects.equals(telepass, that.telepass) &&
                Objects.equals(totemId, that.totemId) &&
                Objects.equals(accessTime, that.accessTime);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Parchimetro{");
        sb.append("telepass-userId=").append(telepass.getUserId());
        sb.append(", totemId=").append(totemId);
        sb.append(", accessTime=").append(accessTime);
        sb.append("}");
        return sb.toString();
    }
}
