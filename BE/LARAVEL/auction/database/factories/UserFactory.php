<?php

namespace Database\Factories;

use App\Models\User;
use Illuminate\Database\Eloquent\Factories\Factory;

class UserFactory extends Factory
{
    protected $model = User::class;

    public function definition()
    {
        return [
            'UserName' => $this->faker->unique()->userName,
            'PassWord' => bcrypt('password'), // Mật khẩu mặc định, bạn có thể thay đổi
            'Avatar' => $this->faker->imageUrl(),
            'Name' => $this->faker->name,
            'DateOfBirth' => $this->faker->date(),
            'Gender' => $this->faker->randomElement(['male', 'female']),
            'Phone' => $this->faker->phoneNumber,
            'Email' => $this->faker->unique()->safeEmail,
            'AddressId' => $this->faker->regexify('[A-Z]{5}[0-4]{5}'),
            'IsBan' => $this->faker->randomElement(['banned', 'unbanned']),
            'Verify' => $this->faker->randomElement(['verified', 'unverified']),
        ];
    }

    /**
     * Indicate that the user is verified.
     *
     * @return \Illuminate\Database\Eloquent\Factories\Factory
     */
    public function verified()
    {
        return $this->state(function (array $attributes) {
            return [
                'Verify' => 'verified',
            ];
        });
    }

    /**
     * Indicate that the user is banned.
     *
     * @return \Illuminate\Database\Eloquent\Factories\Factory
     */
    public function banned()
    {
        return $this->state(function (array $attributes) {
            return [
                'IsBan' => 'banned',
            ];
        });
    }
}
