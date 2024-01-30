package fr.iut;


import fr.iut.club.Putter;
import fr.iut.club.Wood;

/**
 * A caddy has several clubs and knows which club to use depending on conditions
 */
public class Caddy {
    private Club putter = (Club) new Putter();
    private Club wood = (Club) new Wood();

    /**
     * default empty constructor *
     */
    public Caddy() {  }

    /**
     * Return
     * @param conditions
     * @return
     */
    public Club getClub(final Conditions conditions) {
        switch (conditions) {
            case GREEN:
                return putter;
            case FAIRWAY:
                return wood;
            default:
                return putter;
        }
    }

    public static interface Club {
        /**
         * @param force     float between 0 and 1 to indicate the force vector value
         * @param direction the direction assuming North is PI/2 rad
         * @param ball      the ball to move
         */
        void shoot(final double force, final double direction, Ball ball);
    }
}
