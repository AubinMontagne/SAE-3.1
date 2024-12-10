import Metier.*;
import Vue.*;
public class Controleur
{
    private Metier metier;
    private FrameBanque frameBanque;
    private FrameParam frameParam;


    public Controleur()
    {
        this.metier = new Metier();
    }

    // Get

    public Metier getMetier()
    {
        return this.metier;
    }

    // Set

    public void setMetier(Metier metier)
    {
        this.metier = metier;
    }
}