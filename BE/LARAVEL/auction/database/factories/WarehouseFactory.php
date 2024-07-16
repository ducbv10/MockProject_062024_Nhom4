<?php

namespace Database\Factories;

use App\Models\Warehouse;
use Illuminate\Database\Eloquent\Factories\Factory;

class WarehouseFactory extends Factory
{
    protected $model = Warehouse::class;

    public function definition()
    {
        return [
            'Position' => $this->faker->word,
            'Quantity' => $this->faker->randomFloat(2, 0, 1000),
            'importDate' => $this->faker->dateTimeBetween('-1 month', 'now'),
            'exportDate' => $this->faker->dateTimeBetween('now', '+1 month'),
        ];
    }
}
