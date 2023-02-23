package me.blockbattles.util;

import me.blockbattles.handlers.BannerHandler;
import me.blockbattles.handlers.WallBannerHandler;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockHandler;

public class RegisterBlocks {
    public RegisterBlocks() {
        BlockHandler bannerHandler = new BannerHandler();
        BlockHandler wallBannerHandler = new WallBannerHandler();

        //Block blackBanner = Block.BLACK_BANNER.withHandler(bannerHandler);
        //Block blackWallBanner = Block.BLACK_WALL_BANNER.withHandler(wallBannerHandler);

        //Block blueBanner = Block.BLUE_BANNER.withHandler(bannerHandler);
        //Block blueWallBanner = Block.BLUE_WALL_BANNER.withHandler(wallBannerHandler);

        for (Block block : Block.values()) {
            if(block.toString().toLowerCase().contains("wall_banner") && block.toString().toLowerCase().contains("banner")) {
                System.out.println(block);
                block.withHandler(wallBannerHandler);
            }
            else if(block.toString().toLowerCase().contains("banner")) {
                System.out.println(block);
                block.withHandler(bannerHandler);
            }
        }
    }
}
