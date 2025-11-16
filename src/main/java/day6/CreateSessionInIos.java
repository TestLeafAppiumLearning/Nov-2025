package day6;

import org.testng.annotations.Test;
import wrappers.GenericWrappers;

public class CreateSessionInIos extends GenericWrappers {

    @Test
    public void runCode() {
        launchIosApp("2CDB9EC8-51BA-42D8-8973-3FBD791771D4","","","");
        sleep(3000);
        closeApp();
    }
}
