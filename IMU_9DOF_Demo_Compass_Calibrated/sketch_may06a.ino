#include <SoftwareSerial.h>

SoftwareSerial mavoieserie(11, 10); // (RX, TX) (pin Rx BT, pin Tx BT)

void setup()
{
    // Ouvre la voie série avec l'ordinateur
    Serial.begin(38400);
    // Ouvre la voie série avec le module BT
    mavoieserie.begin(38400);
}

void loop() // run over and over
{
    if (mavoieserie.available()) {
        Serial.write(mavoieserie.read());
    }
    if (Serial.available()) {
        mavoieserie.write(Serial.read());
    }
}
