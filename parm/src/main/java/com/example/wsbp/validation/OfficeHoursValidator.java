package com.example.wsbp.validation;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class OfficeHoursValidator implements IValidator<Float> {
    //TODO 間接的に値が変わるTextFieldではこのバリデーターが動作しないらしいので、解決方法を探る
    private final static float MAX_OFFICE_HOURS = 8;
    private final static float MIN_OFFICE_HOURS = 0.25F;

    public OfficeHoursValidator() {
    }

    @Override
    public void validate(IValidatable<Float> validatable) {
        float officeHours = validatable.getValue();
        System.out.println(officeHours);
        ValidationError error = new ValidationError();
        if (officeHours < MIN_OFFICE_HOURS) {
            error.setMessage(String.format("%f時間未満の情報は入力できません", MIN_OFFICE_HOURS));
            validatable.error(error);
            return;
        }
        if (officeHours > MAX_OFFICE_HOURS) {
            error.setMessage("この日の合計勤務時間が8時間を超えようとしています。1日の合計勤務時間は8時間を超えてはいけません");
            validatable.error(error);
        }
    }
}
