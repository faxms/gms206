importPackage(Packages.tools.packet);
var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
		cm.getPlayer().getClient().getSession().writeAndFlush(SLFCGPacket.SetIngameDirectionMode(true, false, false, false));
        cm.getPlayer().getClient().getSession().writeAndFlush(SLFCGPacket.MakeBlind(1, 0x00ff, 0x00f0, 0x00f0, 0x00f0, 0, 0));
        statusplus(500);
		Packages.server.Timer.MapTimer.getInstance().schedule(function () {
		cm.getPlayer().getClient().getSession().writeAndFlush(SLFCGPacket.InGameDirectionEvent("Map/Effect3.img/BossLucid/Lucid5", 0x02, 0, 0x59, 0x24, 0x1, 0x1, 0, 0x1, 0, 0));
		cm.getPlayer().getClient().getSession().writeAndFlush(CField.NPCPacket.getNpcTalkNoButton(3003250,37,3000,"#face6#어머, 이를 어째? 꿈이 무너지나봐요~!"));
        }, 2000);

        Packages.server.Timer.MapTimer.getInstance().schedule(function () {
	cm.getPlayer().getClient().getSession().writeAndFlush(SLFCGPacket.MakeBlind(0, 0, 0, 0, 0, 1500, 0));
	cm.getPlayer().getClient().getSession().writeAndFlush(SLFCGPacket.playSE("Sound/SoundEff.img/ArcaneRiver/phase2"));
	cm.getPlayer().getClient().getSession().writeAndFlush(SLFCGPacket.InGameDirectionEvent("Map/Effect3.img/BossLucid/Lucid2", 0x02, 0, 0x59, 0x24, 0xA, 0x1, 0, 0x1, 0, 0));
	cm.getPlayer().getClient().getSession().writeAndFlush(SLFCGPacket.InGameDirectionEvent("Map/Effect3.img/BossLucid/Lucid3", 0x02, 0, -140, 0x64, 0xB, 0x1, 0, 0x1, 0, 0));
	cm.getPlayer().getClient().getSession().writeAndFlush(SLFCGPacket.InGameDirectionEvent("Map/Effect3.img/BossLucid/Lucid4", 0x02, 0, 0x59, 0x24, 0x1, 0x1, 0, 0x1, 0, 0));
	statusplus(2000);
        }, 5000);

        Packages.server.Timer.MapTimer.getInstance().schedule(function () {
		cm.getPlayer().getClient().getSession().writeAndFlush(SLFCGPacket.MakeBlind(1, 0x00ff, 0x00f0, 0x00f0, 0x00f0, 1300, 0));
		statusplus(1600);
        }, 7500);

        Packages.server.Timer.MapTimer.getInstance().schedule(function () {
			cm.getPlayer().getClient().getSession().writeAndFlush(SLFCGPacket.SetIngameDirectionMode(false, true));
			statusplus(700);
        }, 9100);
        cm.dispose();
    }
}

function statusplus(time) {
    cm.getPlayer().getClient().getSession().writeAndFlush(SLFCGPacket.InGameDirectionEvent("", 1, time));
}