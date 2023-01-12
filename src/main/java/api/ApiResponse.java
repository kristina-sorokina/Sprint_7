package api;

public class ApiResponse {
    private int id;

    public ApiResponse(int id) {
        this.id = id;
    }

    public ApiResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
