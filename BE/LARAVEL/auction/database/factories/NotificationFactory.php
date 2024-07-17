<?php

namespace Database\Factories;

use App\Models\Notification;
use Illuminate\Database\Eloquent\Factories\Factory;

class NotificationFactory extends Factory
{
    protected $model = Notification::class;

    public function definition()
    {
        return [
            'UserId' => $this->faker->uuid,
            'Content' => $this->faker->sentence,
            'Title' => $this->faker->words(3, true),
        ];
    }
}
