package day6;

import org.testng.annotations.Test;
import wrappers.GenericWrappers;

public class CreateSessionWithPreInstallAppInIos extends GenericWrappers {

    @Test
    public void runCode() {
        launchIosApp("2CDB9EC8-51BA-42D8-8973-3FBD791771D4", "", "com.example.apple-samplecode.UICatalog", "");
        sleep(3000);
        closeApp();
    }
}
