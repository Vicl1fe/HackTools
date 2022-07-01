package burp;


import com.darkerbox.Main;

public class BurpExtender implements IBurpExtender {
    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {

        new Main().start(callbacks);
    }
}
