public class Controleur
{
    private FrameBanque frameBanque;
    private FrameParam frameParam;

    public Controleur()
    {
        this.frameBanque = new FrameBanque(this);
        this.frameParam = new FrameParam(this);
    }
}
