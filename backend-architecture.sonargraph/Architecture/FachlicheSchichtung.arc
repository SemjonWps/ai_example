
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
    }
    
    public artifact common
    {
        include "**/common/**"
    }
}
