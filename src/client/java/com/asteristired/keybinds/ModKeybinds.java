package com.asteristired.keybinds;

import com.asteristired.Backslot.Backslot;
import com.asteristired.Backslot.Satchel;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import static com.asteristired.ArthursMod.LOGGER;

public class ModKeybinds {

    private static KeyBinding equipbackslotKeyBind;

    public static void setUpBackslotKB() {
        equipbackslotKeyBind = new KeyBinding(
                "key.arthursmod.getbackslot", // Translation Key
                InputUtil.Type.KEYSYM,
                InputUtil.GLFW_KEY_C, // Default key (C)
                "category.arthursmod.keybinds" // Category in controls menu
        );
        LOGGER.info("Registered keybinding: {}", equipbackslotKeyBind.getTranslationKey());
    }

    public static void Initalise() {
        setUpBackslotKB();

        Satchel.DoKeybindCheck(equipbackslotKeyBind);
        Backslot.DoKeyBindCheck(equipbackslotKeyBind);
    }


}
