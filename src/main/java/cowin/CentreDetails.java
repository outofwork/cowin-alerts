package cowin;

/**
 * @author Ramesh Gupta
 * created on 20/05/21
 */
public class CentreDetails {

    private int age;  // Age Limit  18 or 45
    private String pinCode; // Pincode of the hospital
    private String address; // Any text of the hospital name. uses String contains to check
    private int centreId; // Find the hospital using centreId.


    public int getAge() {
        return age;
    }

    public String getPinCode() {
        return pinCode;
    }

    public String getAddress() {
        return address;
    }

    public int getCentreId() {
        return centreId;
    }


    public static final class CentreDetailsBuilder {
        private int age;
        private String pinCode;
        private String address;
        private int centreId;

        public CentreDetailsBuilder() {
        }

        public static CentreDetailsBuilder aCentreDetails() {
            return new CentreDetailsBuilder();
        }

        public CentreDetailsBuilder setAge(int age) {
            this.age = age;
            return this;
        }

        public CentreDetailsBuilder setPinCode(String pinCode) {
            this.pinCode = pinCode;
            return this;
        }

        public CentreDetailsBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public CentreDetailsBuilder setCentreId(int centreId) {
            this.centreId = centreId;
            return this;
        }

        public CentreDetails build() {
            CentreDetails centreDetails = new CentreDetails();
            centreDetails.address = this.address;
            centreDetails.pinCode = this.pinCode;
            centreDetails.centreId = this.centreId;
            centreDetails.age = this.age;
            return centreDetails;
        }
    }
}
