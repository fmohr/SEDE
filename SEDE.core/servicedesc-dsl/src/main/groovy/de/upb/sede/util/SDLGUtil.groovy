package de.upb.sede.util

import de.upb.sede.ISDLBase
import de.upb.sede.SDL
import de.upb.sede.SDLBase
import de.upb.sede.SDLReader

class SDLGUtil {

    public static ISDLBase read(@DelegatesTo(SDL) Closure sdlScript) {
        SDLReader reader = new SDLReader()
        reader.readClosure(sdlScript)
        def collects = reader.collections
        def sdlBase = reader.SDLBase
        return sdlBase;
    }
}
