package model.rockets.ice_protection_rocket;

import model.Airspace;
import model.rockets.Rocket;

public class IceProtectionRocket extends Rocket {
    public IceProtectionRocket(double range, int height, Airspace a){
        super( range, height,"IPR",a);
    }
}
