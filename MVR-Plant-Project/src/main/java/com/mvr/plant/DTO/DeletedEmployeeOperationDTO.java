package com.mvr.plant.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeletedEmployeeOperationDTO
{
    private Long plantId;
    private String employeeId;
    private String employeeName;
    private String designation;
    private Long mobileNo;
    private String address;
    private LocalDate dateOfBirth;
    private LocalDate dateOfJoining;
    private LocalDate dateOfLeaving;

    private Long operationId;
    private Boolean attendanceAm;
    private Boolean attendancePm;
    private LocalDate operationDate;
}
