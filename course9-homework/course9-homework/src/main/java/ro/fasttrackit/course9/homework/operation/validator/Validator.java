package ro.fasttrackit.course9.homework.operation.validator;

import ro.fasttrackit.course9.homework.exception.ValidationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

import static java.lang.String.join;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.util.Collections.unmodifiableCollection;
import static java.util.Optional.ofNullable;

public class Validator {
    private Collection<ValidationException> exceptions = new ArrayList<>();

    public ValidatorAssertion check(Supplier<Boolean> condition) {
        return check(ofNullable(condition.get()).orElse(false));
    }

    public ValidatorAssertion check(boolean condition) {
        return new ValidatorAssertion(condition);
    }

    public Validator checkFields(String resource, FieldValidator.FieldChecker... fieldChecks) {
        new FieldValidator(resource).checkFields(fieldChecks).ifPresent(exceptions::add);
        return this;
    }

    public Validator checkFields(FieldValidator.FieldChecker... fieldChecks) {
        new FieldValidator().checkFields(fieldChecks).ifPresent(exceptions::add);
        return this;
    }

    public void ifOk(Runnable validations) {
        if (exceptions.isEmpty()) {
            validations.run();
        }
    }

    public void mergeValidatorExceptions(Validator externalValidator) {
        exceptions.addAll(externalValidator.getExceptions());
    }

    public Collection<ValidationException> getExceptions() {
        return unmodifiableCollection(exceptions);
    }

    private void addException(String messages, int code) {
        exceptions.add(new ValidationException(messages, code));
    }

    public void fail(String... message) {
        fail(HTTP_BAD_REQUEST, message);
    }

    public void fail(int code, String... message) {
        String joinedMsg = ofNullable(message)
                .map(msg -> join("", msg))
                .orElse("");

        Validator.this.addException(joinedMsg, code);
    }

    public class ValidatorAssertion {
        private final boolean conditionPassed;

        private ValidatorAssertion(boolean conditionPassed) {
            this.conditionPassed = conditionPassed;
        }

        public ContinuedAssertion ifFalse(String... message) {
            return ifFalse(HTTP_BAD_REQUEST, message);
        }

        public ContinuedAssertion ifFalse(int code, String... message) {
            addExceptionIfNeeded(!conditionPassed, code, message);
            return new ContinuedAssertion(conditionPassed);
        }

        public ContinuedAssertion ifFalse(Supplier<String> message) {
            return ifFalse(HTTP_BAD_REQUEST, message.get());
        }

        public ValidatorAssertion ifTrue(String... message) {
            return ifTrue(HTTP_BAD_REQUEST, message);
        }

        public ValidatorAssertion ifTrue(int code, String... message) {
            addExceptionIfNeeded(conditionPassed, code, message);
            return new ContinuedAssertion(conditionPassed);
        }

        public ContinuedAssertion ifFalse(Runnable continueValidation) {
            if (!conditionPassed) {
                continueValidation.run();
            }
            return new ContinuedAssertion(conditionPassed);
        }

        public ContinuedAssertion ifTrue(Runnable continueValidation) {
            if (conditionPassed) {
                continueValidation.run();
            }
            return new ContinuedAssertion(conditionPassed);
        }

        private void addExceptionIfNeeded(boolean shouldAdd, int code, String... message) {
            if (shouldAdd) {
                fail(code, message);
            }
        }

        public class ContinuedAssertion extends ValidatorAssertion {
            private ContinuedAssertion(boolean conditionPassed) {
                super(conditionPassed);
            }

            public ValidatorAssertion check(boolean condition) {
                return new ValidatorAssertion(condition);
            }
        }
    }
}
