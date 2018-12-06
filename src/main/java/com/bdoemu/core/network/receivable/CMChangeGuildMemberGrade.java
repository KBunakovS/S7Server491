// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildMemberRankType;
import com.bdoemu.gameserver.model.team.guild.events.ChangeGuildMemberGradeEvent;

public class CMChangeGuildMemberGrade extends ReceivablePacket<GameClient> {
    private long accountId;
    private EGuildMemberRankType rank;

    public CMChangeGuildMemberGrade(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.accountId = this.readQ();
        this.rank = EGuildMemberRankType.values()[this.readC()];
        this.readC();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Guild guild = player.getGuild();
            if (guild != null) {
                guild.onEvent(new ChangeGuildMemberGradeEvent(player, guild, this.accountId, this.rank));
            }
        }
    }
}