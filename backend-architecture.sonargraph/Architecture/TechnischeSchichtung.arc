

artifact technischeschichtung
{
    include "**/kartenverkauf/**"
    
    relaxed artifact configuration
    {
        include "**/configuration/**"
    }
    
    artifact adapters
    {
        include "**/adapters/**"
        connect to primary_ports, secondary_ports
    }
    
    artifact application_services
    {
        include "**/application/services**"
        connect to primary_ports, secondary_ports
    }
    
    artifact primary_ports
    {
        include "**/application/ports/in/**"
    }
    
    artifact secondary_ports
    {
        include "**/application/ports/out/**"
    }
    
    public artifact domain
    {
        include "**/domain/**"
    }
}
