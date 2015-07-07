package com.github.bingoohuang.springrest.boot.filter;

import com.google.common.base.Charsets;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

public class BufferedRequestWrapper extends HttpServletRequestWrapper {
    private static final class BufferedServletInputStream extends ServletInputStream {
        private final ServletInputStream inputStream;
        private ByteArrayInputStream bais;

        public BufferedServletInputStream(ByteArrayInputStream bais, ServletInputStream inputStream) {
            this.bais = bais;
            this.inputStream = inputStream;
        }

        @Override
        public int available() {
            return this.bais.available();
        }

        @Override
        public int read() {
            return this.bais.read();
        }

        @Override
        public int read(byte[] buf, int off, int len) {
            return this.bais.read(buf, off, len);
        }

        @Override
        public boolean isFinished() {
            return inputStream.isFinished();
        }

        @Override
        public boolean isReady() {
            return inputStream.isReady();
        }

        @Override
        public void setReadListener(ReadListener listener) {
            inputStream.setReadListener(listener);
        }
    }

    private byte[] mBodyBuffer;

    public BufferedRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);

        InputStream in = request.getInputStream();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead = -1;
        while ((bytesRead = in.read(buffer)) > 0) {
            baos.write(buffer, 0, bytesRead);
        }
        mBodyBuffer = baos.toByteArray();
    }

    public String getRequestBody() {
        return new String(mBodyBuffer, Charsets.UTF_8);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(mBodyBuffer);
        return new BufferedServletInputStream(in, super.getInputStream());
    }
}
