package com.example.admin.model.houses;

import com.example.admin.entity.HouseStatus;
import com.example.admin.validation.general.groups.Create;
import com.example.admin.validation.house.CreateHouseUnique;
import com.example.admin.validation.general.groups.Edit;
import com.example.admin.validation.house.EditHouseUnique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
@CreateHouseUnique(
        number = "number",
        street = "street",
        city = "city",
        groups = Create.class
)
@EditHouseUnique(
        number = "number",
        street = "street",
        city = "city",
        groups = Edit.class
)
public record HouseRequest(
        @NotBlank(message = "Поле не може бути порожнім", groups = {Create.class, Edit.class})
        @Size(max=100, message = "Розмір поля має бути не більше 100 символів", groups = {Create.class, Edit.class})
        String city,
        @NotBlank(message = "Поле не може бути порожнім", groups = {Create.class, Edit.class})
        @Size(max=200, message = "Розмір поля має бути не більше 200 символів", groups = {Create.class, Edit.class})
        String street,
        @NotBlank(message = "Поле не може бути порожнім", groups = {Create.class, Edit.class})
        @Size(max=10, message = "Розмір поля має бути не більше 10 символів", groups = {Create.class, Edit.class})
        String number,
        @NotNull(message = "Поле не може бути порожнім", groups = {Create.class, Edit.class})
        HouseStatus status,
        @NotNull(message = "Поле не може бути порожнім", groups = {Create.class, Edit.class})
        Long chairmanId
) {
}
