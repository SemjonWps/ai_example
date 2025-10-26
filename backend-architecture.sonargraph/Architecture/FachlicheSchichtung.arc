
artifact fachlicheschichtung
{
    include "backend/**"
    
    artifact filmauswahl
    {
        include "**/filmauswahl/**"
    }
    
    artifact kartenverkauf
    {
        include "**/kartenverkauf/**"
        
        relaxed artifact common
        {
            priority -1
            include "**"
        }
        
        artifact kartenausstellung
        {
            include "**/kartenausstellung/**"
            connect to sitzplatzvergabe, programm
        }
        
        artifact preisberechnung
        {
            include "**/preisberechnung/**"
            connect to sitzplatzvergabe, programm
        }
        
        artifact sitzplatzvergabe
        {
            include "**/sitzplatzvergabe/**"
            connect to programm
        }
        
        artifact programm
        {
            include "**/programm/**"
        }
        
        artifact zahlung
        {
            include "**/zahlung/**"
        }
    }
    
    public artifact common
    {
        include "**/common/**"
    }
}
