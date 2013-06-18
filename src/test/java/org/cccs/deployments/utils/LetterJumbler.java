package org.cccs.deployments.utils;

/**
 * User: boycook
 * Date: Aug 27, 2010
 * Time: 4:52:08 PM
 */
public class LetterJumbler {

    public static void main(String[] args) {
        LetterJumbler jumbler = new LetterJumbler();
        jumbler.jumble("foobar");
    }

    private void jumble(String word) {
        for (int i=0; i<word.length(); i++) {
            Character c = word.charAt(i);
        }
//        System.out.println(word);
    }
}
