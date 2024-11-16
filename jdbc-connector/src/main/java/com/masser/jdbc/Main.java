package com.masser.jdbc;

import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        Connector connector = new Connector(
                "localhost",
                "3306",
                "root", "",
                "pokemonSC"
        );

        Map<String, String> additionalColumns = Map.of(
                "pokedex_entry", "INT NOT NULL",
                "ability_id", "INT NOT NULL"
        );

        Map<String, String[]> foreignKeys = Map.of(
                "pokedex_entry", new String[]{"pokemon", "pokedex_entry"},
                "ability_id", new String[]{"abilities", "ability_id"}
        );

        String compositePrimaryKey = "pokedex_entry, ability_id";

        connector.createTable(
                "pokemon_abilities",
                null,
                null,
                additionalColumns,
                foreignKeys,
                compositePrimaryKey
        );
    }
}
