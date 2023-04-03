/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 Apryse Group NV
    Authors: Apryse Software.

    This program is offered under a commercial and under the AGPL license.
    For commercial licensing, contact us at https://itextpdf.com/sales.  For AGPL licensing, see below.

    AGPL licensing:
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.itextpdf.html2pdf.attach;

import java.util.Stack;

/**
 * State machine to push, pop, peek,... {@link ITagWorker} instances to and from the {@link Stack}.
 */
public class State {

    /** The stack. */
    private Stack<ITagWorker> stack;

    /**
     * Instantiates a new {@link State} instance.
     */
    public State() {
        stack = new Stack<>();
    }

    /**
     * Gets the stack.
     *
     * @return the stack
     */
    public Stack<ITagWorker> getStack() {
        return stack;
    }

    /**
     * Pushes a {@link ITagWorker} instance to the stack.
     *
     * @param tagWorker the tag worker
     */
    public void push(ITagWorker tagWorker) {
        stack.push(tagWorker);
    }

    /**
     * Pops a {@link ITagWorker} from the stack.
     *
     * @return the tag worker
     */
    public ITagWorker pop() {
        return stack.pop();
    }

    /**
     * Peeks at the {@link ITagWorker} at the top of the stack.
     *
     * @return the tag worker at the top
     */
    public ITagWorker top() {
        return stack.peek();
    }

    /**
     * Checks if the stack is empty.
     *
     * @return true, if the stack is empty
     */
    public boolean empty() {
        return stack.size() == 0;
    }

}
