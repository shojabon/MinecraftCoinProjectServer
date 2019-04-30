package MinecraftCoinServer;

import CommandExtentionServer.CommandExtentionServer;
import SecureSocketChipV1.SSCV1;
import SecureSocketChipV1.interfaces.SSCEvent;
import SecureSocketChipV1.interfaces.SSCVCommand;

import java.util.UUID;

public class MinecraftCoinServer extends SSCEvent implements SSCVCommand{

    CommandExtentionServer server;

    public MinecraftCoinServer(int port){
        server = new CommandExtentionServer(port, this, this);
    }

    @Override
    public void onCommand(SSCV1 sscv1, String command, String[] args, UUID uuid) {
    }


}
