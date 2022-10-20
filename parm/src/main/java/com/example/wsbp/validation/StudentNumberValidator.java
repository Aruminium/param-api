package com.example.wsbp.validation;

import com.example.wsbp.AdminProperties;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import java.util.regex.Pattern;

public class StudentNumberValidator implements IValidator<String> {
    private final static String STUDENT_NUMBER_PATTERN = "^[bmd]\\d+$";
    private final static String ERROR_MESSAGE = "学籍番号はbまたはm、dと7桁の数字で入力してください";
    private final Pattern pattern;
    private final FeedbackPanel feedbackPanel;

    public StudentNumberValidator(FeedbackPanel feedbackPanel) {
        this.pattern = Pattern.compile(STUDENT_NUMBER_PATTERN);
        this.feedbackPanel = feedbackPanel;
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        final String studentNumber = validatable.getValue();
        ValidationError error = new ValidationError();
        if (!pattern.matcher(studentNumber).matches()) {
            if (studentNumber.equals(AdminProperties.STUDENT_NUMBER)) return;
            feedbackPanel.setVisible(true);
            error.setMessage(ERROR_MESSAGE);
            validatable.error(error);
        }
    }
}
