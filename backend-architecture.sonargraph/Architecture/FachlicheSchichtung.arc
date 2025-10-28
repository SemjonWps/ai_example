
artifact fachlicheschichtung
{
    include "backend/**"
    
    artifact filmauswahl
    {
        include "**/kino/filmauswahl/**"
    }
    
    artifact kartenverkauf
    {
        include "**/kino/kartenverkauf/**"
        
        relaxed artifact common
        {
            priority -1
            include "**"
        }
        
        artifact kartenerstellung
        {
            include "**/kartenerstellung/**"
            connect to sitzplatzvergabe, filmauswahl
        }
        
        artifact preisberechnung
        {
            include "**/preisberechnung/**"
            connect to sitzplatzvergabe, filmauswahl
        }
        
        artifact sitzplatzvergabe
        {
            include "**/sitzplatzvergabe/**"
            connect to filmauswahl
        }
        
        artifact filmauswahl
        {
            include "**/filmauswahl/**"
        }
        
        artifact zahlung
        {
            include "**/zahlung/**"
        }
    }
    
    public artifact common
    {
        include "**/kino/common/**"
    }
}
