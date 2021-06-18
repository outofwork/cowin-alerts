# cowin-alerts
Script to alert when Cowin slots are available


### How to user

    1. Change the CowinApp class file to add you centre.

    Example:
                .setAge(18)
                .setPinCode("273002")
                .setAddress("AIRFORCE")
                .setCentreId(625575)
                .build());

    Addd multiple CentreDetails class Objects to create alert for multiple centres.

    2. Change ALERT_INTERVAL to run the alert at intervals.
        ExampleL 30 will run the alerts every 30 seconds.
