package com.example.solutiontofarming.Validators;

import com.example.solutiontofarming.data.Field;
import com.example.solutiontofarming.data.Lease;
import com.example.solutiontofarming.data.Owner;
import com.example.solutiontofarming.data.Rent;

public class AgriLandValidator {
    public static boolean validateField(Field field) {
        // Validate fieldType
        if (field.getType() == null || field.getType().isEmpty()) {
            return false;
        }

        // Validate fieldArea
        if (field.getArea() <= 0) {
            return false;
        }

        // Validate fieldAreaUnit
        if (field.getUnit() == null || field.getUnit().isEmpty()) {
            return false;
        }

        return true;
    }

    public static boolean validateDescription(String description) {
        // Validate description
        if (description == null || description.isEmpty()) {
            return false;
        }

        return true;
    }

    public static boolean validateLease(Lease lease) {
        // Validate duration
        if (lease.getDuration() == null || lease.getDuration().isEmpty()) {
            return false;
        }

        // Validate startDate
        if (lease.getStartDate() == null || lease.getStartDate().isEmpty()) {
            return false;
        }

        // Validate endDate
        if (lease.getEndDate() == null || lease.getEndDate().isEmpty()) {
            return false;
        }

        return true;
    }

    public static boolean validateRent(Rent rent) {
        // Validate rentType
        if (rent.getType() == null || rent.getType().isEmpty() || rent.getType().equals("Select Rent Type")) {
            return false;
        }

        // Validate rentAmount
        if (rent.getAmount() < 0) {
            return false;
        }

        // Validate rentSharePercent
        if (rent.getShare_percent() < 0 || rent.getShare_percent() > 100) {
            return false;
        }

        return true;
    }

    public static boolean validateOwner(Owner owner) {
        // Validate ownerName
        if (owner.getName() == null || owner.getName().isEmpty()) {
            return false;
        }

        // Validate contact
        String regexPattern = "^[6789]\\d{9}$";
        return owner.getContact().matches(regexPattern);
    }
}
