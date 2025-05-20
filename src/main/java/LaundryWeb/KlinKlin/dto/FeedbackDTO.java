package LaundryWeb.KlinKlin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FeedbackDTO {

    private String id;

    @NotBlank(message = "User ID wajib diisi")
    private String userId;

    @NotBlank(message = "Isi feedback tidak boleh kosong")
    private String isi;
}
