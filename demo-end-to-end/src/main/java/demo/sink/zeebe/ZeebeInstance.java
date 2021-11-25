package demo.sink.zeebe;

public class ZeebeInstance<T> {

    private String instanceId;
    private Integer version;
    private T payload;

    public ZeebeInstance(T payload) {
        this.payload = payload;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
