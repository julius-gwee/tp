package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_FROM = new Prefix("from/");
    public static final Prefix PREFIX_TO = new Prefix("to/");
    public static final Prefix PREFIX_TAG_NAME = new Prefix("tn/");
    public static final Prefix PREFIX_TAG_CATEGORY = new Prefix("tc/");
    public static final Prefix PREFIX_TAG_COLOUR = new Prefix("tcol/");
    public static final Prefix PREFIX_TAG_DESCRIPTION = new Prefix("td/");
    public static final Prefix PREFIX_NEW_TAG_NAME = new Prefix("nn/");

}
