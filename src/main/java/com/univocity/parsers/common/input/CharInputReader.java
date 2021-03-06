/*******************************************************************************
 * Copyright 2014 uniVocity Software Pty Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.univocity.parsers.common.input;

import com.univocity.parsers.common.*;

import java.io.*;

/**
 * The definition of a character input reader used by all uniVocity-parsers that extend {@link AbstractParser}.
 *
 * <p> This interface declares basic functionalities to provide a common input manipulation structure for all parser classes.
 * <p> Implementations of this interface <b>MUST</b> convert the sequence of newline characters defined by {@link Format#getLineSeparator()} into the normalized newline character provided in {@link Format#getNormalizedNewline()}.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:parsers@univocity.com">parsers@univocity.com</a>
 * @see com.univocity.parsers.common.Format
 */
public interface CharInputReader {

	/**
	 * Initializes the CharInputReader implementation with a {@link java.io.Reader} which provides access to the input.
	 *
	 * @param reader A {@link java.io.Reader} that provides access to the input.
	 */
	void start(Reader reader);

	/**
	 * Stops the CharInputReader from reading characters from the {@link java.io.Reader} provided in {@link CharInputReader#start(Reader)} and closes it.
	 */
	void stop();

	/**
	 * Returns the next character in the input provided by the active {@link java.io.Reader}.
	 * <p> If the input contains a sequence of newline characters (defined by {@link Format#getLineSeparator()}), this method will automatically converted them to the newline character specified in {@link Format#getNormalizedNewline()}.
	 * <p> A subsequent call to this method will return the character after the newline sequence.
	 *
	 * @return the next character in the input. '\0' if there are no more characters in the input or if the CharInputReader was stopped.
	 */
	char nextChar();

	/**
	 * Returns the number of characters returned by {@link CharInputReader#nextChar()} at any given time.
	 *
	 * @return the number of characters returned by {@link CharInputReader#nextChar()}
	 */
	long charCount();

	/**
	 * Returns the number of newlines read so far.
	 *
	 * @return the number of newlines read so far.
	 */
	long lineCount();

	/**
	 * Skips characters in the input until the given number of lines is discarded.
	 *
	 * @param lineCount the number of lines to skip from the current location in the input
	 */
	void skipLines(long lineCount);

	/**
	 * Collects the comment line found on the input.
	 *
	 * @return the text found in the comment from the current position.
	 */
	String readComment();

	/**
	 * Indicates to the input reader that the parser is running in "escape" mode and
	 * new lines should be returned as-is to prevent modifying the content of the parsed value.
	 *
	 * @param escaping flag indicating that the parser is escaping values and line separators are to be returned as-is.
	 */
	void enableNormalizeLineEndings(boolean escaping);

	/**
	 * Returns the line separator by this character input reader. This could be the line separator defined
	 * in the {@link Format#getLineSeparator()} configuration, or the line separator sequence identified automatically
	 * when {@link CommonParserSettings#isLineSeparatorDetectionEnabled()} evaluates to {@code true}.
	 *
	 * @return the line separator in use.
	 */
	char[] getLineSeparator();
}
