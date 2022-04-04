package com.goldrush;

public class NPCs {
    private NPC[] npc;
    private int size;

    public NPCs(String[] names){
        size = names.length;
        npc = new NPC[names.length];
        for(int i = 0; i < names.length; i++){
            npc[i] = new NPC(names[i]);
        }
    }

    public NPC[] getNPCs() {return npc;}
    public int getSize() {return size;}
}
