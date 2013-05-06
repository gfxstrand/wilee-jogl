/*
 * Copyright © 2012-2013 Jason Ekstrand.
 *  
 * Permission to use, copy, modify, distribute, and sell this software and its
 * documentation for any purpose is hereby granted without fee, provided that
 * the above copyright notice appear in all copies and that both that copyright
 * notice and this permission notice appear in supporting documentation, and
 * that the name of the copyright holders not be used in advertising or
 * publicity pertaining to distribution of the software without specific,
 * written prior permission.  The copyright holders make no representations
 * about the suitability of this software for any purpose.  It is provided "as
 * is" without express or implied warranty.
 * 
 * THE COPYRIGHT HOLDERS DISCLAIM ALL WARRANTIES WITH REGARD TO THIS SOFTWARE,
 * INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS, IN NO
 * EVENT SHALL THE COPYRIGHT HOLDERS BE LIABLE FOR ANY SPECIAL, INDIRECT OR
 * CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE,
 * DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER
 * TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE
 * OF THIS SOFTWARE.
 */
package net.jlekstrand.wheatley.jogl;

import java.io.File;

import javax.media.opengl.*;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.Animator;

import net.jlekstrand.wheatley.Compositor;

public class MainWindow
{
    public static void main(String[] args) {
        GLProfile.initSingleton();
        GLProfile glp = GLProfile.get(GLProfile.GLES2);
        GLCapabilities caps = new GLCapabilities(glp);

        final GLWindow window = GLWindow.create(caps);
        window.setSize(640, 480);
        window.setVisible(true);
        window.setTitle("Wheatley");

        window.addWindowListener(new WindowAdapter() {
            public void windowDestroyNotify(WindowEvent arg0) {
                System.exit(0);
            };
        });

        GLRenderer renderer = new GLRenderer(window);
        window.addGLEventListener(renderer);

        while (! renderer.isInitialized())
            window.display();

        Compositor compositor = new Compositor();
        compositor.display.addSocket("wayland-1");
        compositor.setRenderer(renderer);

        NEWTSeat seat = new NEWTSeat(compositor, window);
        compositor.display.addGlobal(seat);

        compositor.run();
    }
}
