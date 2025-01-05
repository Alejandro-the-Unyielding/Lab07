package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    private static final Comparator<String> byDays = new sortByDays();
    private static final Comparator<String> byOrder = new sortByOrder();


    @Override
    public Comparator<String> sortByDays() {
        return byDays;
    }

    private enum Month {
        JANUARY(31),
        Febuary(28),
        March(31),
        April(30),
        May(31),
        June(30),
        July(31),
        August(31),
        September(30),
        October(31),
        November(30),
        December(31);

        private final int days;

        Month(final int days){
            this.days=days;
        }

        public static Month fromString(final String name) {
            Objects.requireNonNull(name);
            try {
                return valueOf(name);
            } catch (IllegalArgumentException e) {
                // Fallback to manual scan before giving up
                Month match = null;
                for (final Month month: values()) {
                    if (month.toString().toLowerCase(Locale.ROOT).startsWith(name.toLowerCase(Locale.ROOT))) {
                        if (match != null) {
                            throw new IllegalArgumentException(
                                name + " is ambiguous: both " + match + " and " + month + " would be valid matches",
                                e
                            );
                        }
                        match = month;
                    }
                }
                if (match == null) {
                    throw new IllegalArgumentException("No matching months for " + name, e);
                }
                return match;
            }
        }
    }

    @Override
    public Comparator<String> sortByOrder() {
        return byOrder;
    }

    private static final class sortByDays implements Comparator<String>{

        @Override
        public int compare (String o1, String o2){
            final int var1= Month.fromString(o1).days;
            final int var2= Month.fromString(o2).days;
            return Integer.compare(var1, var2);
        }


    }

    private static final class sortByOrder implements Comparator<String>{
        @Override
        public int compare (String o1, String o2){
            final Month var1 = Month.fromString(o1);
            final Month var2 = Month.fromString(o2);
            return Integer.compare(var1.ordinal(), var2.ordinal());
        }
    }

}
