package game;

import city.cs.engine.*;

public class PortalBody extends StaticBody {
    //portal which brings you to a new level
    private static final Shape portalShape = new CircleShape(5);
    BodyImage portal =
            new BodyImage("data/portal.png",15f);

    public PortalBody(World w) {
        super(w, portalShape);
        addImage(portal);
    }
}
