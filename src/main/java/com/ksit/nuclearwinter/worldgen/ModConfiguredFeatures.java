package com.ksit.nuclearwinter.worldgen;

import com.ksit.nuclearwinter.NuclearWinter;
import com.ksit.nuclearwinter.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_URAN_ORE_KEY = registerKey("uranium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_LEAD_ORE_KEY = registerKey("lead_ore");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> overworldUranOres = List.of(OreConfiguration.target(stoneReplaceable,
                ModBlocks.URANIUM_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateReplaceables,
                ModBlocks.DEEPSLATE_URANIUM_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> overworldLeadOres = List.of(OreConfiguration.target(stoneReplaceable,
                ModBlocks.LEAD_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateReplaceables,
                ModBlocks.DEEPSLATE_LEAD_ORE.get().defaultBlockState()));

        register(context, OVERWORLD_URAN_ORE_KEY, Feature.ORE, new OreConfiguration(overworldUranOres, 9));
        register(context, OVERWORLD_LEAD_ORE_KEY, Feature.ORE, new OreConfiguration(overworldLeadOres, 9));
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(NuclearWinter.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}