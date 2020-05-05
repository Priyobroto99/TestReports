package com.priyo.testreport.models;

public class TestStep {

        private String stepNo;
        private String stepName;
        private String stepStatus;

        public TestStep(String stepNo, String stepName, String stepStatus) {
            this.stepNo = stepNo;
            this.stepName = stepName;
            this.stepStatus = stepStatus;
        }

        public String getStepNo() {
            return stepNo;
        }

        public String getStepName() {
            return stepName;
        }

        public String getStepStatus() {
            return stepStatus;
        }

}
