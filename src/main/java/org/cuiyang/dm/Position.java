package org.cuiyang.dm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * 坐标
 *
 * @author cuiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    /** 索引 */
    private int index;
    /** x坐标 */
    private int x;
    /** y坐标 */
    private int y;

    public static Position parse(String position) {
        if (position == null) {
            return null;
        }
        String[] split = position.split("\\|");
        if (split.length == 3 && parseInt(split[0]) > -1) {
            return new Position(parseInt(split[0]), parseInt(split[1]), parseInt(split[2]));
        } else {
            return null;
        }
    }

    public static List<Position> parseList(String position) {
        List<Position> positions = new ArrayList<>();
        if (position == null) {
            return positions;
        }
        String[] split = position.split("\\|");
        for (String str : split) {
            String[] split2 = str.split(",");
            if (split2.length == 3 && parseInt(split2[0]) > -1) {
                positions.add(new Position(parseInt(split2[0]), parseInt(split2[1]), parseInt(split2[2])));
            }
        }
        return positions;
    }
}
