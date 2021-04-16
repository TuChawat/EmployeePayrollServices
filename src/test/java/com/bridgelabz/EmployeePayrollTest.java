package com.bridgelabz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class EmployeePayrollTest {
    @Test
    public void givenEmployees_ShouldWriteDataToFile_AndMatchWithTheNumberOfEntries() {
        EmployeePayrollData[] employeePayrollData = {
                new EmployeePayrollData(123, "Akash", 2100000),
                new EmployeePayrollData(456, "Naman", 1200000),
                new EmployeePayrollData(789, "Utkarsh", 1000000)    };
        EmployeePayrollService employeePayrollService = new EmployeePayrollService(Arrays.asList(employeePayrollData));
        employeePayrollService.writeEmployeePayrollDataTOFile(EmployeePayrollService.IOService.FILE_IO);
        Assertions.assertTrue(Files.exists(Paths.get("./" + EmployeePayrollFileIOService.PAYROLL_FILE)));
        EmployeePayrollFileIOService.printData();
        Assertions.assertEquals(3, EmployeePayrollFileIOService.countEntries());
    }

    @Test
    public void givenEmployees_whenReadingFromFile_ShouldMatchWithNumOfEntries() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        long count = employeePayrollService.readEmployeePayrollDataFromFile(EmployeePayrollService.IOService.FILE_IO);
        Assertions.assertEquals(3, count);
    }
}

