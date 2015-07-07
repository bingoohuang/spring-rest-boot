package com.github.bingoohuang.springrest.boot.filter;


import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A decorating {@code Writer} that writes all characters written to it to its
 * underlying streams. Named after the Unix 'tee' command. Not thread safe.
 *
 * @author Osman KOCAK
 */
final class TeeWriter extends Writer {
    private final List<Writer> writers;

    /**
     * Creates a new {@code TeeWriter}.
     *
     * @param writers the writers to write to.
     * @throws NullPointerException if {@code writers} is {@code null} or
     *                              if it contains a {@code null} reference.
     */
    TeeWriter(Writer... writers) {
        this(Arrays.asList(writers));
    }

    /**
     * Creates a new {@code TeeWriter}.
     *
     * @param writers the writers to write to.
     * @throws NullPointerException if {@code writers} is {@code null} or
     *                              if it returns a {@code null} reference.
     */
    TeeWriter(Iterable<? extends Writer> writers) {
        this.writers = new ArrayList<Writer>();
        for (Writer writer : writers) {
            checkNotNull(writer);
            this.writers.add(writer);
        }
    }

    @Override
    public TeeWriter append(char c) throws IOException {
        for (Writer writer : writers) {
            writer.append(c);
        }
        return this;
    }

    @Override
    public TeeWriter append(CharSequence sequence) throws IOException {
        for (Writer writer : writers) {
            writer.append(sequence);
        }
        return this;
    }

    @Override
    public TeeWriter append(CharSequence sequence, int start, int end)
            throws IOException {
        return append(sequence.subSequence(start, end));
    }

    @Override
    public void write(int c) throws IOException {
        for (Writer writer : writers) {
            writer.write(c);
        }
    }

    @Override
    public void write(char[] buf) throws IOException {
        for (Writer writer : writers) {
            writer.write(buf);
        }
    }

    @Override
    public void write(char[] buf, int off, int len) throws IOException {
        for (Writer writer : writers) {
            writer.write(buf, off, len);
        }
    }

    @Override
    public void write(String str) throws IOException {
        for (Writer writer : writers) {
            writer.write(str);
        }
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        write(str.substring(off, off + len));
    }

    @Override
    public void flush() throws IOException {
        for (Writer writer : writers) {
            writer.flush();
        }
    }

    @Override
    public void close() {
        for (Writer writer : writers) {
            closeQuietly(writer);
        }
    }

    private void closeQuietly(Writer writer) {
        try {
            writer.close();
        } catch (Exception e) {
            // Ignore
        }
    }
}