/**
 *
 */
package org.fc.entity;

/**
 * @author pskopek
 *
 */
public class Catch {

    private Long id;

    private Long teamId;
    private int round;
    private String sector;

    private String fishType;
    private int length;
    private int cips;

    public Catch() {
        length = 0;
        calcCIPS();
        fishType = "";
    }

    /**
     * Za každú rybu je 100 bodov plus za každý centimeter 20.
     * Bodovaná dľžka sa zaokrúhluje na celé centimetre smerom hore
     * 221 mm ryba je 100 za rybu + 23*20 460 za dľžku teda spolu 560
     *                                   Ďuro
     */

    public void calcCIPS() {

        int cmUp = length / 10 + (length % 10 == 0 ? 0 : 1);
        if (length != 0)
            cips = 100 + cmUp * 20;
        else
            cips = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String toString() {
        return "[" + id + ": R=" + getRound() + ", S=" + getSector() + "-" + length + "," + cips + "]";
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getFishType() {
        return fishType;
    }

    public void setFishType(String fishType) {
        this.fishType = fishType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
        calcCIPS();
    }

    public int getCips() {
        return cips;
    }

    public void setCips(int cips) {
        this.cips = cips;
    }


}
